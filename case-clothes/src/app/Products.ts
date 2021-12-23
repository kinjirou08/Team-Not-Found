export interface Products{
    id: number;
    name: string;
    price: number;
    description: string;
    categories: {
        id: number;
        category: SVGStringList;
    }
    imageUrl: string;
    totalQuantity: number;

}