package Demo.Controller;

import Demo.Model.Add;
import Demo.Model.Auction;
import Demo.Model.Offer;
import Demo.Service.AddService;
import Demo.Service.AuctionService;
import Demo.Service.OfferService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest( controllers = TradeController.class )
class TradeControllerTest {

    @MockBean
    private AddService addService;

    @MockBean
    private AuctionService auctionService;

    @MockBean
    private OfferService offerService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getAdds() throws Exception {
        when( addService.findAll() ).thenReturn( new ArrayList<Add>() );
        mockMvc
            .perform( get("/api/sell_add") )
            .andExpect( status().isOk() );
    }

    @Test
    void addSellingAdd() throws Exception {
        when( addService.createSellingAdd( any(Long.class), any(Long.class), any(Float.class), any() ) )
                .thenReturn( Optional.of( new Add(1L, "Test", 10.0F) ) );
        mockMvc
                .perform(
                        post("/api/sell_add")
                                .param("sellerId", String.valueOf(1))
                                .param("cardId", String.valueOf(1))
                                .param("price", String.valueOf(10))
                                .param("description", "Test")
                )
                .andExpect( status().isOk() );
        doThrow( RuntimeException.class ).when( addService ).saveObject( any() );
        mockMvc
                .perform(
                        post("/api/sell_add")
                                .param("sellerId", String.valueOf(1))
                                .param("cardId", String.valueOf(1))
                                .param("price", String.valueOf(10))
                                .param("description", "Test")
                )
                .andExpect( status().isInternalServerError() );
    }

    @Test
    void deleteSellAdd() throws Exception {
        when( addService.findById( any(Long.class) ) ).thenReturn( Optional.empty() );
        mockMvc
            .perform( delete("/api/sell_add/{id}", 1) )
            .andExpect( status().isNotFound() );
        when( addService.findById( any(Long.class) ) ).thenReturn( Optional.of( new Add() ) );
        mockMvc
                .perform( delete("/api/sell_add/{id}", 1) )
                .andExpect( status().isOk() );

    }

    @Test
    void getAuctions() throws Exception {
        when( auctionService.findAll() ).thenReturn( new ArrayList<Auction>() );
        mockMvc
                .perform( get("/api/auction") )
                .andExpect( status().isOk() );
    }

    @Test
    void createAuction() throws Exception {
        when( addService.findById( any(Long.class) ) ).thenReturn(  Optional.of( new Add() ) );
        mockMvc
                .perform( post("/api/auction").header( "xAddId", 1 ) )
                .andExpect( status().isOk() );

        when( addService.findById( any(Long.class) ) ).thenReturn(  Optional.empty() );
        mockMvc
                .perform( post("/api/auction").header( "xAddId", 1 ) )
                .andExpect( status().isNotFound() );
    }

    @Test
    void deleteById() throws Exception {
        when( auctionService.findById( any(Long.class) ) ).thenReturn( Optional.empty() );
        mockMvc
                .perform( delete("/api/auction/{id}", 1) )
                .andExpect( status().isNotFound() );
        when( auctionService.findById( any(Long.class) ) ).thenReturn( Optional.of( new Auction() ) );
        mockMvc
                .perform( delete("/api/auction/{id}", 1) )
                .andExpect( status().isOk() );
    }

    @Test
    void createOffer() throws Exception {
        when( auctionService.findById( any(Long.class) ) ).thenReturn( Optional.of( new Auction() ) );
        when( offerService.createOffer( any(Long.class), any( Long.class ), any( Double.class) ) ).thenReturn(Optional.of(new Offer()));
        mockMvc
            .perform(
                    post("/api/auction/{id}", 1)
                        .param("offererId", String.valueOf(1) )
                        .param("price", String.valueOf(10.0) )
                        .param("id", String.valueOf(1) )
            )
            .andExpect( status().isOk() );
    }
}