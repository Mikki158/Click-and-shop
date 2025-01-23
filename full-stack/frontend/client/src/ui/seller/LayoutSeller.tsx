import React from 'react'
import HeaderSeller from "./HeaderSeller"
import Footer from '../Footer'
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