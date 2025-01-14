import React from 'react'
import Header from './Header'
import Footer from './Footer'
import { Toaster } from 'react-hot-toast'
import AuthProvider from '../AuthProvider'

const Layout = ({children}:{children:React.ReactNode}) => {
  return <>
    <AuthProvider>
      <Header />
      {children}
      <Footer />
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
    </AuthProvider>
    
  </>
}

export default Layout