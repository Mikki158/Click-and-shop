import { create } from "zustand";

interface AuthState {
    isAuthenticated: boolean;
    setIsAuthenticated: (auth: boolean) => void;
}

const useAuthStore = create<AuthState>((set) => ({
    isAuthenticated: !!sessionStorage.getItem("accessToken"),
    setIsAuthenticated: (auth) => set({ isAuthenticated: auth }),
}));

export const authStore = useAuthStore;