import axios from "axios";

export const getData = async(endpoint:string) => {
    try {

        const response = await fetch(endpoint, {
            method: "GET",
            headers: {
                "Content-type": "application/json",
            }
        });

        let config = {
            method: 'get',
            url: endpoint,
            headers:{
                "Content-type": "application/json",
            },
            mode: 'no-cors',
        };

        console.log(config);
        
        
 
        // axios.request(config)
        // .then((response) => {
        //     console.log(response.data);
        //     const data = response.data;
        //     return response.data;
        // })
        // .catch((error) => {
        //     console.log(error);  
        //     return null;          
        // })

        // console.log(response);
        
        
        if(!response.ok) {
            throw new Error("Data fetching Error" + response?.statusText);
        }

        const data = await response.json();
        
        return data;

    } catch (error) {
        console.log('Error while fetching data ', error);
        throw error;
    }
}