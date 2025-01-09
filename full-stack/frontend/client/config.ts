interface Config{
    baseUrl:string
}

const checkConfig = (server:string):Config | {} => {
    let config : Config | {} = {};

    switch (server) {
        case "production":
            config = {
                baseUrl: "",
            };
            break;

        case "local":
            config = {
                baseUrl: "https://click-and-shop.ru"
            };
            break;
    
        default:
            break;
    }

    return config;
};

export const selectServer = 'local';
export const config = checkConfig(selectServer) as Config;
