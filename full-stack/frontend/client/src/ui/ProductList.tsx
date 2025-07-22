import Container from './Container'
import Title from './Title'
import { Link } from 'react-router-dom'
import Pagination from './Pagination'

const ProductList = () => {
  return (
    <div>
      <Container>
        <Pagination />
      </Container>
    </div>
  )
  
}

export default ProductList
