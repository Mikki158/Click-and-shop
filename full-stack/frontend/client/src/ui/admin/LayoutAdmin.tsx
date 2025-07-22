import HeaderAdmin from "./HeaderAdmin"
import { Toaster } from 'react-hot-toast'

const LayoutAdmin = ({children}:{children:React.ReactNode}) => {
    return<>
        <HeaderAdmin/>
        {children}
        <Toaster 
            position='bottom-right'
            reverseOrder={false}
            gutter={8}
            containerClassName=''
            toastOptions={{
                style: {
                backgroundColor: "black",
                color: "white",
                }
            }}
            />
    </>
}

export default LayoutAdmin;