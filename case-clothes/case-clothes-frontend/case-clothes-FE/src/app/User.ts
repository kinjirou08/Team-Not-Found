
export interface User{
    id: number;
    username: string;
    password: string;
    firstName: string;
    lastName: string;
    email: string;
    phoneNumber: string;
    address: String;
    role: {
        id: number;
        role: string;
    }

}