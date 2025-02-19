export interface CategoryProps {
  id: number;
  name: string;
  base: string;
  parentCategory: number
}

export interface ProductProps {
  id: number;
  name: string;
  regularPrice: number;
  discountedPrice: number;
  description: string;
  categoryId: string;
  brandId: string;
  imagePaths: [string];
  quantity: number;
}

export interface BrandProps {
  id: number;
  brandName: string;
  sellerId: string;
}