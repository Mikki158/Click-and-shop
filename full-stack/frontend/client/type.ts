export interface CategoryProps {
  _id: number;
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
  imagePaths: [string];
}