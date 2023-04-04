package guru.sfg.beer.order.service.services;

import guru.sfg.beer.order.service.domain.BeerDto;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;
import java.util.UUID;

@ConfigurationProperties(prefix = "sfg.brewery", ignoreUnknownFields = false)
@Service
public class BeerServiceImpl implements BeerService {

  public static final String BEER_PATH_V1 = "/api/v1/beer/";
  public static final String BEER_UPC_V1 = "/api/v1/beerUpc/";

  private final RestTemplate restTemplate;

  private String beerServiceHost;

  public BeerServiceImpl(final RestTemplateBuilder restTemplateBuilder) {
    this.restTemplate = restTemplateBuilder.build();
  }

  @Override
  public Optional<BeerDto> getBeerById(final UUID uuid) {
    return Optional.of(restTemplate.getForObject(beerServiceHost + BEER_PATH_V1 + uuid.toString(), BeerDto.class));
  }

  @Override
  public Optional<BeerDto> getBeerByUpc(final String upc) {
    return Optional.of(restTemplate.getForObject(beerServiceHost + BEER_UPC_V1 + upc, BeerDto.class));
  }

  public void setBeerServiceHost(final String beerServiceHost) {
    this.beerServiceHost = beerServiceHost;
  }
}
