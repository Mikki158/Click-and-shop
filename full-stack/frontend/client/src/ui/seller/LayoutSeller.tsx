import React from 'react'
import HeaderSeller from "./HeaderSeller"
<<<<<<< Updated upstream
import Footer from '../Footer'
=======
>>>>>>> Stashed changes
import { Toaster } from 'react-hot-toast'

const LayoutSeller = ({children}:{children:React.ReactNode}) => {
  return <>
    <HeaderSeller />
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

export default LayoutSeller;