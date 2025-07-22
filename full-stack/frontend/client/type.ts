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
  rating: number;
  ratingCount: number;
  brandId: string;
  imagePaths: [string];
  quantity: number;
}

export interface BrandProps {
  id: number;
  brandName: string;
  sellerId: string;
}

export interface ReviewProps {
  id: number,
  created: string,
  updated: strinf,
  productId: number,
  userId: number,
  username: string,
  photoUrl: string,
  status: string,
  rating: number,
  reviewText: string,
  parentReviewId: number,
  imagePaths: [string]
}

export interface PickupPoint {
  id: number,
  address: string,
  phoneNumber: string
}