package guru.sfg.beer.order.service.web.mappers;

import guru.sfg.beer.order.service.domain.BeerDto;
import guru.sfg.beer.order.service.domain.BeerOrderLine;
import guru.sfg.beer.order.service.services.BeerService;
import guru.sfg.beer.order.service.web.model.BeerOrderLineDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.Optional;

public abstract class BeerOrderLineMapperDecorator implements BeerOrderLineMapper {

  private BeerService beerService;
  private BeerOrderLineMapper beerOrderLineMapper;

  @Autowired
  public void setBeerService(final BeerService beerService) {
    this.beerService = beerService;
  }

  @Autowired
  @Qualifier("delegate")
  public void setBeerOrderLineMapper(final BeerOrderLineMapper beerOrderLineMapper) {
    this.beerOrderLineMapper = beerOrderLineMapper;
  }

  @Override
  public BeerOrderLineDto beerOrderLineToDto(final BeerOrderLine line) {
    final BeerOrderLineDto beerOrderLineDto = beerOrderLineMapper.beerOrderLineToDto(line);
    final Optional<BeerDto> beerByUpc = beerService.getBeerByUpc(line.getUpc());

    beerByUpc.ifPresent(beerDto -> {
          beerOrderLineDto.setBeerName(beerDto.getBeerName());
          beerOrderLineDto.setBeerStyle(beerDto.getBeerStyle());
          beerOrderLineDto.setPrice(beerDto.getPrice());
          beerOrderLineDto.setBeerId(beerDto.getId());
        }
    );
    return beerOrderLineDto;
  }
}
