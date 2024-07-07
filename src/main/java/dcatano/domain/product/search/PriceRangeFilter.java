package dcatano.domain.product.search;

import lombok.Getter;

import java.util.Optional;

@Getter
public class PriceRangeFilter {
    private final Double lowerBound;
    private final Double upperBound;

    public PriceRangeFilter(Double lowerBound, Double upperBound) throws ProductPriceRangeException {
        if(lowerBound == null && upperBound == null) {
            throw new ProductPriceRangeException(Messages.NULL_RANGE.getMessage());
        }
        if(Optional.ofNullable(lowerBound).orElse(Double.MIN_VALUE) > Optional.ofNullable(upperBound).orElse(Double.MAX_VALUE)) {
            throw new ProductPriceRangeException(Messages.INVALID_RANGE.getMessage());
        }
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
    }
}
