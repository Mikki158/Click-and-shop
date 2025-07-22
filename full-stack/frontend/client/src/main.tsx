import { createRoot } from 'react-dom/client'
import './index.css'
import App from './App.tsx'
import Layout from './ui/Layout.tsx'
import { createBrowserRouter, Outlet, RouterProvider } from 'react-router-dom'
import Product from './pages/Product.tsx'
import Category from './pages/Category.tsx'
import Profile from './pages/Profile.tsx'
import Cart from './pages/Cart.tsx'
import Favorite from './pages/Favorite.tsx'
import Orders from './pages/Orders.tsx'
import Success from './pages/Success.tsx'
import Cancel from './pages/Cancel.tsx'
import NotFound from './pages/NotFound.tsx'
import Login from './pages/Login.tsx'
import CreateSeller from './pages/CreateSeller.tsx'
import ReviewDashboard from './pages/ReviewDashboard.tsx'
import Home from './ui/seller/Home.tsx'
import LayoutSeller from './ui/seller/LayoutSeller.tsx'
import Products from '././ui/seller/Products.tsx'
import NewProduct from '././ui/seller/NewProduct.tsx'
import EditProduct from '././ui/seller/EditProduct.tsx'
import UploadImage from '././ui/seller/UploadImage.tsx'
import Supplies from '././ui/seller/Supplies.tsx'
import CreateSupply from './pages/seller/CreateSupply'
import ProductReviews from './pages/seller/ProductReviews'
import { ToastContainer } from 'react-toastify'
import 'react-toastify/dist/ReactToastify.css'
import LayoutAdmin from './ui/admin/LayoutAdmin'
import HomeAdmin from './ui/admin/HomeAdmin'
import Users from './pages/admin/Users'
import Warehouse from './pages/admin/Warehouse'
import PickupPoint from './pages/admin/PickupPoint'
import ReviewRequest from './pages/admin/ReviewRequest'
import OrderDetails from './pages/OrderDetails'
import { redirect } from "react-router-dom";

const RouterLayout = () => {
  return (
    <Layout>
      <Outlet />
    </Layout>
  )
}

const SellerLayout = () => {
  return (
    <>
      <LayoutSeller>
        <Outlet />
      </LayoutSeller>
      <ToastContainer
        position="top-right"
        autoClose={5000}
        hideProgressBar={false}
        newestOnTop={false}
        closeOnClick={false}
        rtl={false}
        pauseOnFocusLoss
        draggable
        pauseOnHover
        theme="light"
      />
    </>
  )
}

const AdminLayout = () => {
  return (
    <>
      <LayoutAdmin>
        <Outlet />
      </LayoutAdmin>
      <ToastContainer
        position="top-right"
        autoClose={5000}
        hideProgressBar={false}
        newestOnTop={false}
        closeOnClick={false}
        rtl={false}
        pauseOnFocusLoss
        draggable
        pauseOnHover
        theme="light"
      />
    </>
  )
}

const router = createBrowserRouter([
  {
    path:'/',
    element:<RouterLayout />,
    children:[
      {
        path:'/',
        element:<App />,
      },
      {
        path:'/product',
        element:<Product />,
      },
      {
        path:'/product/:id',
        element:<Product />,
      },
      {
        path:'/category',
        element:<Category />,
      },
      {
        path:'/category/:id',
        element:<Category />,
      },
      {
        path:'/login',
        element:<Login/>
      },
      {
        path:'/profile',
        element:<Profile />,
      },
      {
        path:'/cart',
        element:<Cart />,
      },
      {
        path:'/favorite',
        element:<Favorite />,
      },
      {
        path:'/orders',
        element:<Orders />,
      },
      {
        path: '/order/:id',
        element:<OrderDetails/>
      },
      {
        path:'/widgets/login',
        element:<Login />,
      },
      {
        path:'/success',
        element:<Success />,
      },
      {
        path:'/cancel',
        element:<Cancel />,
      },
      {
        path:'/createSeller',
        element:<CreateSeller />
      },
      {
        path:'/reviews',
        element:<ReviewDashboard/>
      },
      {
        path:'*',
        element:<NotFound />,
      }
    ]
  },
  {
    path:'/seller',
    element:<SellerLayout />,
    loader: () => {
      const rolesString = localStorage.getItem("roles") || "";
      const roles = rolesString.split(",").map(r => r.trim());
      if (!roles.includes("Seller")) {
        throw redirect("/");
      }
      return null;
    },
    children:[
      {
        path:'',
        element:<Home/>
      },
      {
        path:'products',
        element:<Products/>
      },
      {
        path:'newProduct',
        element:<NewProduct />
      },
      {
        path:'editProduct/:id',
        element:<EditProduct />
      },
      {
        path:'image',
        element:<UploadImage />
      },
      {
        path:'supplies',
        element:<Supplies/>
      },
      {
        path:'createSupply',
        element:<CreateSupply/>
      },
      {
        path:'reviews',
        element:<ProductReviews/>
      }
    ]
  },
  {
    path:'/admin',
    element:<AdminLayout />,
    loader: () => {
      const rolesString = localStorage.getItem("roles") || "";
      const roles = rolesString.split(",").map(r => r.trim());
      if (!roles.includes("Admin")) {
        throw redirect("/");
      }
      return null;
    },
    children:[
      {
        path:'',
        element:<HomeAdmin />
      },
      {
        path:'users',
        element:<Users/>
      },
      {
        path:'warehouse',
        element:<Warehouse/>
      },
      {
        path:'pickupPoints',
        element: <PickupPoint />
      },
      {
        path:'reviews',
        element:<ReviewRequest/>
      }
    ]
  }
])



createRoot(document.getElementById('root')!).render(
  <RouterProvider router={router}/>
)
