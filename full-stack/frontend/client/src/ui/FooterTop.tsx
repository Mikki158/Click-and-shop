import Container from './Container'

const FooterTop = () => {

    const incentives = [
        {
            name: "Бесплатная доставка",
            imageSrc:
              "https://www.svgrepo.com/show/445701/delivery-fast.svg",
            description:
              "На самом деле это не бесплатно, мы просто включаем это в стоимость продуктов. Кто-то платит за это, и это не мы.",
          },
          {
            name: "10 лет гарантия",
            imageSrc:
              "https://www.svgrepo.com/show/286468/certificate.svg",
            description:
              "Если он сломается в течение первых 10 лет, мы заменим его. После этого вы будете предоставлены сами себе.",
          },
          {
            name: "Обмены",
            imageSrc:
              "https://www.svgrepo.com/show/216002/exchange-change.svg",
            description:
              "Если вам что-то не понравится, обменяйте это у кого-нибудь из ваших друзей на что-нибудь из их коллекции. Но не отправляйте это сюда.",
          },
    ]

  return (
    <Container className='py-0'>
        <div className='rounded-2xl bg-[#f6f6f6] px-6 py-16 sm:p-16'>
            <div className='mx-auto max-w-xl lg:max-w-none'>
                <div className='text-center'>
                    <h2 className='text-xl sm:text-2xl font-bold
                    tracking-tight text-gray-900'>
                        Описание номер один
                    </h2>
                </div>
            </div>
            <div className='mx-auto mt-12 grid max-w-sm
            grid-cols-1 pag-8 sm:max-w-none lg:grid-cols-3'>
                {incentives.map((item) => (
                    <div key={item?.name} className='text-center
                    sm:flex sm:text-left lg:block lg:text-center'>
                        <div className='sm:flex-shrink-0'>
                            <div className='flex-root'>
                                <img src={item?.imageSrc} alt="image" 
                                className='mx-auto h-16 w-16'/>
                            </div>
                        </div>
                        <div className='mt-3 sm:ml-6 lg:ml-0'>
                            <h3 className='text-base font-medium
                            text-gray-900'>
                                {item?.name}
                            </h3>
                            <p className='mt-2 text-sm text-gray-500'>
                                {item?.description}
                            </p>
                        </div>
                    </div>
                ))}
            </div>
        </div>
    </Container>
  )
}

export default FooterTop
