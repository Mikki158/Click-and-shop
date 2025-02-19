// Import the functions you need from the SDKs you need
import { initializeApp } from "firebase/app";
import { getAuth } from "firebase/auth";
import { getFirestore } from "firebase/firestore";
import { getStorage } from "firebase/storage";
// TODO: Add SDKs for Firebase products that you want to use
// https://firebase.google.com/docs/web/setup#available-libraries

// Your web app's Firebase configuration
const firebaseConfig = {
  apiKey: "AIzaSyAaOeCgaYDtHqOsPzOS6DWtt83d8-OICTU",
  authDomain: "click-and-shop-2b62a.firebaseapp.com",
  projectId: "click-and-shop-2b62a",
  storageBucket: "click-and-shop-2b62a.firebasestorage.app",
  messagingSenderId: "1099173529000",
  appId: "1:1099173529000:web:01e239eaffdecbb117899d",
  measurementId: "G-91KFX4640Z"
};

// Initialize Firebase
export const app = initializeApp(firebaseConfig);
export const auth = getAuth();
export const db = getFirestore();
export const storage = getStorage();