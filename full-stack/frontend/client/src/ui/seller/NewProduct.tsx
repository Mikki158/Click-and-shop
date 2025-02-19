import React, { useEffect, useState } from "react";
import apiClient from '../../apiClient'
<<<<<<< Updated upstream
=======
import { BrandProps, CategoryProps } from "../../../type";
import { toast } from 'react-toastify'
>>>>>>> Stashed changes

const NewProduct: React.FC = () => {

    const [selectedFiles, setSelectedFiles] = useState<File[]>([]);
    const [previews, setPreviews] = useState<string[]>([]);

<<<<<<< Updated upstream
    const [brands, setBrands] = useState([]);
    const [brandId, setBrandId] = useState(null)
    
    const [categorys, setCategorys] = useState([])
    const [categoryId, setCategoryId] = useState(null)
=======
    const [brands, setBrands] = useState<BrandProps[]>([]);
    const [brandId, setBrandId] = useState('')
    
    const [categorys, setCategorys] = useState<CategoryProps[]>([])
    const [categoryId, setCategoryId] = useState('')
>>>>>>> Stashed changes

    const [name, setName] = useState("")
    const [regularPrice, setRegularPrice] = useState("")
    const [discountedPrice, setDiscountedPrice] = useState("")
    const [description, setDescription] = useState("")
    let productId = ""

<<<<<<< Updated upstream
    const nameHandler = (event) => {
        setName(event.target.value)
    }

    const regularPriceHandler = (event) => {
        setRegularPrice(event.target.value)
    }

    const discountedPriceHandler = (event) => {
        setDiscountedPrice(event.target.value)
    }

    const descriptionHandler = (event) => {
=======
    const nameHandler = (event : React.ChangeEvent<HTMLInputElement>) => {
        setName(event.target.value)
    }

    const regularPriceHandler = (event : React.ChangeEvent<HTMLInputElement>) => {
        setRegularPrice(event.target.value)
    }

    const discountedPriceHandler = (event : React.ChangeEvent<HTMLInputElement>) => {
        setDiscountedPrice(event.target.value)
    }

    const descriptionHandler = (event : React.ChangeEvent<HTMLTextAreaElement>) => {
>>>>>>> Stashed changes
        setDescription(event.target.value)
    }

    useEffect(() => {

      // Получаем брэнды продавца
      apiClient.get("/api/seller/brandList")
      .then((response) => {
        setBrands(response.data)
        console.log("Брэнды ", response.data)
      })
      .catch((error) => {
        console.error("Ошибка при получении категорий ", error)
      })

      // Получаем категории
      apiClient.get("/api/category")
      .then((response) => {
        setCategorys(response.data)
        console.log("Категории ", categorys)
      })
      .catch((error) => {
        console.error("Ошибка при получении категорий ", error)
      })
    }, [])

<<<<<<< Updated upstream
    const handleChangeBrand = (event) => {
=======
    const handleChangeBrand = (event : React.ChangeEvent<HTMLSelectElement>) => {
>>>>>>> Stashed changes
      const selectedBrandId = event.target.value;
      setBrandId(selectedBrandId);
      console.log("Выбран брэнд с id ", selectedBrandId);
    }

<<<<<<< Updated upstream
    const handleChangeCategory = (event) => {
=======
    const handleChangeCategory = (event : React.ChangeEvent<HTMLSelectElement>) => {
>>>>>>> Stashed changes
      const selectCategoryId = event.target.value;
      setCategoryId(selectCategoryId);
      console.log("выбрана категория с id ", selectCategoryId)
    }

    // Обработка выбора файлов
    const handleFileChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        const files = event.target.files;
        if (files) {
            const fileArray = Array.from(files); // Преобразуем FileList в массив
            setSelectedFiles((prevFiles) => [...prevFiles, ...fileArray]);

            // Создаём массив превью изображений
            const previewUrls = fileArray.map((file) => URL.createObjectURL(file));
            setPreviews((prevPreviews) => [...prevPreviews, ...previewUrls]);

            console.log("Файл добавлен")
        }
    };

    // Отправка файлов на backend
    const handleUpload = async () => {
        if (selectedFiles.length === 0) {
            alert("Пожалуйста, выберите файлы для загрузки.");
            return;
        }

        const formData = new FormData();
        selectedFiles.forEach((file) => {
            formData.append('files', file); // Добавляем файлы в formData
        });

        console.log(productId)
        formData.append('productId', productId);

        console.log(formData)

        await apiClient.post("/api/files/addPhoto", formData, {
            headers: {
                "Content-Type": "multipart/form-data",
            },
        })
        .then((response) => {
            //alert("Файл успешно загружен!");
            console.log("Ответ сервера:", response.data);
        })
        .catch((error) => {
            console.error("Ошибка при загрузке файлов:", error);
            //alert("Ошибка при загрузке файлов.");
        });
    };

    // Удаление выбранного файла
    const handleRemoveFile = (index: number) => {
        setSelectedFiles((prevFiles) => prevFiles.filter((_, i) => i !== index));
        setPreviews((prevPreviews) => prevPreviews.filter((_, i) => i !== index));
    };

    const saveProduct = async () => {
      console.log(name)
      console.log(regularPrice)
      console.log(discountedPrice)
      console.log(description)

      //let productId = ""

      await apiClient.post("/api/product/createProduct")
      .then((response) => {
          //setProductId(response.data.id)
          productId = response.data.id
          console.log(response)
      })
      .catch((error) => {
          console.error("Ошибка при создании товара ", error)
      })

      const data = {
          "id": productId,
          "name": name,
          "regularPrice": regularPrice,
          "discountedPrice": discountedPrice,
          "description": description,
          "categoryId": categoryId,
          "brandId": brandId
      }

      console.log(data)

      await apiClient.put('/api/product/updateProduct', data)
      .then((response) => {
<<<<<<< Updated upstream
          console.log(response)
      })
      .catch((error) => {
          console.error("Ошибка при обновлении товара ", error)
=======

        toast.success('Товар был успешно создан!', {
          position: "top-right",
          autoClose: 5000,
          hideProgressBar: false,
          closeOnClick: false,
          pauseOnHover: true,
          draggable: true,
          progress: undefined,
          theme: "light",
          });

        console.log(response)
      })
      .catch((error) => {
        console.error("Ошибка при обновлении товара ", error)
        toast.error('Ошибка при добавлении товара', {
          position: "top-right",
          autoClose: 5000,
          hideProgressBar: false,
          closeOnClick: false,
          pauseOnHover: true,
          draggable: true,
          progress: undefined,
          theme: "light",
          });
>>>>>>> Stashed changes
      })

      handleUpload()
    }

  return (
    <div className="p-8 bg-gray-100 min-h-screen">
      <div className="grid grid-cols-3 gap-4">
        {/* Левая колонка с фото */}
        <div className="space-y-4">
            <label className="block mb-2 text-lg font-medium text-gray-700">
                Загрузить фотографии
            </label>
            <input
                type="file"
                accept="image/*"
                multiple
                onChange={handleFileChange}
                className="block w-full text-sm text-gray-500 file:mr-4 file:py-2 file:px-4 file:border-0 file:text-sm file:font-semibold file:bg-gray-100 file:text-gray-700 hover:file:bg-gray-200"
            />

            {previews.length > 0 && (
                <div className="mt-4 grid grid-cols-3 gap-4">
                {previews.map((preview, index) => (
                    <div key={index} className="relative">
                    <img
                        src={preview}
                        alt={`Preview ${index + 1}`}
                        className="w-full h-32 object-cover rounded border"
                    />
                    <button
                        onClick={() => handleRemoveFile(index)}
                        className="absolute top-1 right-1 bg-red-500 text-white rounded-full w-6 h-6 flex items-center justify-center text-sm"
                    >
                        ✕
                    </button>
                    </div>
                ))}
                </div>
            )}
        </div>

        {/* Правая колонка с формой */}
        <div className="col-span-2 bg-white p-6 rounded-lg shadow-md">
          <h2 className="text-lg font-bold mb-4">Основная информация</h2>
          <div className="space-y-4">
            <div>
              <label className="block text-sm font-medium">Наименование</label>
              <input
                type="text"
                className="w-full border border-gray-300 rounded-md p-2"
                placeholder="Наименование"
                name="name"
                value={name}
                onChange={nameHandler}
              />
            </div>

            <div>
              <label className="block text-sm font-medium">Брэнд</label>
              {/* <input
                type="text"
                className="w-full border border-gray-300 rounded-md p-2"
                placeholder="Категория"
              /> */}
              <select
                onChange={handleChangeBrand}
                value={brandId || ""}
                className="border border-gray-300 rounded-md p-2"
              >
                <option value="" disabled>
                  Выберите брэнд
                </option>
                {brands.map((brand) => (
                  <option key={brand.id} value={brand.id}>
                    {brand.brandName}
                  </option>
                ))}
              </select>
            </div>

            <div>
              <label className="block text-sm font-medium">Категория</label>
              <select
                onChange={handleChangeCategory}
                value={categoryId || ""}
                className="border border-gray-300 rounded-md p-2"
              >
                <option value="" disabled>
                  Выберите категорию
                </option>
                {categorys.map((category) => (
                  <option key={category.id} value={category.id}>
                    {category.name}
                  </option>
                ))}
              </select>
            </div>

            <div>
              <label className="block text-sm font-medium">Регулярная цена</label>
              <input
                type="text"
                className="w-full border border-gray-300 rounded-md p-2"
                placeholder="Регулярная цена"
                name="regularPrice"
                value={regularPrice}
                onChange={regularPriceHandler}
              />
            </div>

            <div>
              <label className="block text-sm font-medium">Цена по скидке</label>
              <input
                type="text"
                className="w-full border border-gray-300 rounded-md p-2"
                placeholder="Цена по скидке"
                name="discountedPrice"
                value={discountedPrice} 
                onChange={discountedPriceHandler}
              />
            </div>

            <div>
              <label className="block text-sm font-medium">Цвет</label>
              <input
                type="text"
                className="w-full border border-gray-300 rounded-md p-2"
                placeholder="Цвет"
              />
              <p className="text-sm text-gray-500 mt-1">
                Укажите цвет товара в этом поле. Чтобы создать несколько вариантов одного и того же
                товара, используйте меню слева.
              </p>
            </div>

            <div>
              <label className="block text-sm font-medium">Описание</label>
              <textarea
                className="w-full border border-gray-300 rounded-md p-2"
                placeholder="Описание"
                name="description"
                value={description}
                onChange={descriptionHandler}
              ></textarea>
            </div>
          </div>

          {/* Размеры */}
          <div className="mt-8">
            <h2 className="text-lg font-bold mb-4">Размеры</h2>
            <div className="grid grid-cols-3 gap-4">
              <input
                type="text"
                className="border border-gray-300 rounded-md p-2"
                placeholder="Размер"
              />
              <input
                type="text"
                className="border border-gray-300 rounded-md p-2"
                placeholder="Рос. размер"
              />
              <input
                type="text"
                className="border border-gray-300 rounded-md p-2"
                placeholder="Цена"
              />
            </div>
            <button className="mt-4 bg-blue-500 text-white px-4 py-2 rounded-md hover:bg-blue-600">
              Добавить
            </button>
          </div>

          {/* Габариты упаковки */}
          <div className="mt-8">
            <h2 className="text-lg font-bold mb-4">Габариты упаковки</h2>
            <div className="grid grid-cols-3 gap-4">
              <input
                type="text"
                className="border border-gray-300 rounded-md p-2"
                placeholder="Длина (см)"
              />
              <input
                type="text"
                className="border border-gray-300 rounded-md p-2"
                placeholder="Ширина (см)"
              />
              <input
                type="text"
                className="border border-gray-300 rounded-md p-2"
                placeholder="Высота (см)"
              />
            </div>
            <div className="mt-4">
              <input
                type="text"
                className="w-full border border-gray-300 rounded-md p-2"
                placeholder="Вес (г)"
              />
            </div>
          </div>

          {/* Кнопки */}
          <div className="mt-8 flex justify-between">
            {/* <button 
                className="bg-gray-500 text-white px-4 py-2 rounded-md hover:bg-gray-600"
                >
              К списку товаров
            </button> */}
            <button 
                className="bg-green-500 text-white px-4 py-2 rounded-md hover:bg-green-600"
                onClick={saveProduct}>
              Создать и завершить
            </button>
          </div>
        </div>
      </div>
    </div>
  );
};

export default NewProduct;
