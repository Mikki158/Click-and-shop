const FormattedPrice = ({ amount }: { amount?: number }) => {
    const formattedAmount = new Number(amount).toLocaleString("ru-RU", {
      style: "currency",
      currency: "RUB",
      minimumFractionDigits: 0,
    });
    return <span>{formattedAmount}</span>;
  };
  
  export default FormattedPrice;