import { create } from "zustand";
import { persist } from "zustand/middleware";
import { ProductProps } from "../../type";
import apiClient from "../apiClient";

interface CartProduct extends ProductProps {
  quantity: number;
}

interface UserType {
  firstName: string;
  lastName: string;
  avatar: string;
  id: string;
}

interface StoreType {
  currentUser: UserType | null;
  cartProduct: CartProduct[];
  getUserInfo: () => Promise<void>;
  fetchCart: () => Promise<void>;
  addToCart: (product: CartProduct) => Promise<void>;
  decreaseQuantity: (productId: number) => Promise<void>;
  removeFromCart: (productId: number) => Promise<void>;
}

const customStorage = {
  getItem: (name: string) => {
    const item = localStorage.getItem(name);
    return item ? JSON.parse(item) : null;
  },
  setItem: (name: string, value: any) => {
    localStorage.setItem(name, JSON.stringify(value));
  },
  removeItem: (name: string) => {
    localStorage.removeItem(name);
  },
};
export const store = create<StoreType>()(
  persist(
    (set) => ({
      currentUser: null,
      cartProduct: [],

      getUserInfo: async () => {
        try {
          const response = await apiClient.get("/api/auth/userInfo");
          set({ currentUser: response.data});
        } catch (error) {
          console.error("Ошибка при загрузке информации пользователя ", error);
          set({ currentUser: null})
        }
      },

      fetchCart: async () => {
        try {
          const response = await apiClient.get("/api/cart");
          const cartProducts = response.data;

          console.log("Получение корзины")
          console.log(response)

          const productDetails = await Promise.all(
            cartProducts.map(async (cartProduct: { id: string, quantity: number }) => {
              const productResponse = await apiClient.get(`/api/product/${cartProduct.id}`);
              
              // Добавляем поле quantity к данным продукта
              return {
                ...productResponse.data, // Данные продукта
                quantity: cartProduct.quantity, // Добавляем поле quantity
              };
            })
          );

          console.log("ТОВАРЫ:")
          console.log(productDetails);

          set({ cartProduct: productDetails});

          console.log(cartProducts);
        } catch (error) {
          console.error("Ошибка при получении корзины ", error);
        }
      },
      
      addToCart: async (product: ProductProps) => {
        try { 

          const temp = {
            "id": product.id,
            "quantity": product.quantity
          }

          await apiClient.post("/api/cart", temp);
          await store.getState().fetchCart();
        } catch (error) {
          console.error("Ошибка при добавлении товара в корзину ", error);
        }
      },

      decreaseQuantity: async (productId: number) => {
        try {
          await apiClient.patch(`/api/cart/${productId}/decrease`);
          await store.getState().fetchCart();
        } catch (error) {
          console.error("Не удалось изменить корзину ", error);
        }
      },

      removeFromCart: async (productId: number) => {
        try {
          await apiClient.delete(`/api/cart/${productId}`);
          await store.getState().fetchCart();
        } catch (error) {
          console.error("Ошибка при удалении товара из корзины ", error);
        }
      },
    }),
    {
      name: "supergear-storage",
      storage: customStorage,
    }
  )
);