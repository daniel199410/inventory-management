package dcatano.domain.product.creation;

import java.util.List;

public interface IProductCreator {
    List<String> create(ProductCreatorDTO productCreatorDTO);
}
