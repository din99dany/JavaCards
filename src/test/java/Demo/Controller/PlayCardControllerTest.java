package Demo.Controller;

import Demo.Model.PlayCard;
import Demo.Service.PlayCardService;
import Demo.Service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest( controllers = PlayCardController.class )
class PlayCardControllerTest {

    @MockBean
    private PlayCardService playCardService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getAdds() throws Exception {
        when( playCardService.findById( any(Long.class) ) ).thenReturn( Optional.of( new PlayCard() ) );
        mockMvc
            .perform( get("/api/play_card/{id}", 1) )
            .andExpect( status().isOk() );

        when( playCardService.findById( any(Long.class) ) ).thenReturn( Optional.empty() );
        mockMvc
                .perform( get("/api/play_card/{id}", 1) )
                .andExpect( status().isNotFound() );
    }

    @Test
    void saveCardOk() throws Exception {
        PlayCard playCard = new PlayCard( 0, "Pikatchu", "Lover", "1998 starters" );

        when(  playCardService.createCard( "Pikatchu", "Lover", "1998 starters" ) )
                .thenReturn( Optional.of(playCard) );
        mockMvc
                .perform( post("/api/play_card")
                        .param( "name", "Pikatchu" )
                        .param( "role", "Lover" )
                        .param( "collection", "1998 starters" )
                )
                .andExpect( status().isOk() );
    }

    @Test
    void saveCardFail() throws Exception {
        when( playCardService.createCard(any(),any(),any()) ).thenReturn( Optional.empty() );
        mockMvc
                .perform( post("/api/play_card")
                        .param( "name", "Pikatchu" )
                        .param( "role", "Lover" )
                        .param( "collection", "1998 starters" ) )
                .andExpect( status().isBadRequest() );
        mockMvc
                .perform( post("/api/play_card") )
                .andExpect( status().isBadRequest() );
    }


    @Test
    void deleteCardOk() throws Exception {
        when( playCardService.findById( any( Long.class ) ) ).thenReturn( Optional.of( new PlayCard() ) );
        mockMvc
                .perform( delete("/api/play_card/{id}", 1) )
                .andExpect( status().isOk() );
    }

    @Test
    void deleteCardFail() throws Exception {
        when( playCardService.findById( any( Long.class ) ) ).thenReturn( Optional.empty() );
        mockMvc
                .perform( delete("/api/play_card/{id}", 1) )
                .andExpect( status().isNotFound() );
    }

    @Test
    void deleteCardEx() throws Exception {
        when( playCardService.findById( any( Long.class ) ) ).thenReturn( Optional.of( new PlayCard() ) );
        doThrow(RuntimeException.class).when(playCardService).deleteById( any( Long.class ) );
        mockMvc
                .perform( delete("/api/play_card/{id}", 1) )
                .andExpect( status().isInternalServerError() );
    }
}