export interface Products {

    id: number;
    name: string;
    description: string;
    price: number;
    categories: {
        id: number;
        category: string;
    }
    imageURL: string;
    totalQuantity: number;
    
}