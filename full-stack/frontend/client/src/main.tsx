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
import Home from './ui/seller/Home.tsx'
import LayoutSeller from './ui/seller/LayoutSeller.tsx'
import Products from '././ui/seller/Products.tsx'
import NewProduct from '././ui/seller/NewProduct.tsx'
import EditProduct from '././ui/seller/EditProduct.tsx'
import UploadImage from '././ui/seller/UploadImage.tsx'
<<<<<<< Updated upstream
=======
import { ToastContainer } from 'react-toastify'
import 'react-toastify/dist/ReactToastify.css'
>>>>>>> Stashed changes

const RouterLayout = () => {
  return (
    <Layout>
      <Outlet />
    </Layout>
  )
}

const SellerLayout = () => {
  return (
<<<<<<< Updated upstream
    <LayoutSeller>
      <Outlet />
    </LayoutSeller>
=======
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
>>>>>>> Stashed changes
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
        path:'*',
        element:<NotFound />,
      }
    ]
  },
  {
    path:'/seller',
    element:<SellerLayout />,
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
      }
    ]
  }
])



createRoot(document.getElementById('root')!).render(
  <RouterProvider router={router}/>
)
