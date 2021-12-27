export interface Products {
    "id": number,
    "name": string,
    "description": string,
    "price": number,
    "categories": {
        "category": string
    },
    "imageURL": string,
    "totalQuantity": number
}