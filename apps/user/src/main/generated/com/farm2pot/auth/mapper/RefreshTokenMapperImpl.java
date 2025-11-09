package com.farm2pot.auth.mapper;

import com.farm2pot.auth.controller.dto.TokenRefresh;
import com.farm2pot.auth.entity.RefreshToken;
import java.time.Instant;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-11-09T02:05:05+0900",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.8 (Microsoft)"
)
@Component
public class RefreshTokenMapperImpl implements RefreshTokenMapper {

    @Override
    public RefreshToken toEntity(TokenRefresh arg0) {
        if ( arg0 == null ) {
            return null;
        }

        RefreshToken.RefreshTokenBuilder refreshToken = RefreshToken.builder();

        if ( arg0.token() != null ) {
            refreshToken.token( arg0.token() );
        }
        if ( arg0.userId() != null ) {
            refreshToken.userId( arg0.userId() );
        }
        if ( arg0.expiryDate() != null ) {
            refreshToken.expiryDate( arg0.expiryDate() );
        }

        return refreshToken.build();
    }

    @Override
    public TokenRefresh toDto(RefreshToken arg0) {
        if ( arg0 == null ) {
            return null;
        }

        Long id = null;
        String token = null;
        Long userId = null;
        Instant expiryDate = null;

        if ( arg0.getId() != null ) {
            id = arg0.getId();
        }
        if ( arg0.getToken() != null ) {
            token = arg0.getToken();
        }
        if ( arg0.getUserId() != null ) {
            userId = arg0.getUserId();
        }
        if ( arg0.getExpiryDate() != null ) {
            expiryDate = arg0.getExpiryDate();
        }

        TokenRefresh tokenRefresh = new TokenRefresh( id, token, userId, expiryDate );

        return tokenRefresh;
    }

    @Override
    public void updateEntityFromDto(TokenRefresh arg0, RefreshToken arg1) {
        if ( arg0 == null ) {
            return;
        }

        if ( arg0.id() != null ) {
            arg1.setId( arg0.id() );
        }
        if ( arg0.token() != null ) {
            arg1.setToken( arg0.token() );
        }
        if ( arg0.userId() != null ) {
            arg1.setUserId( arg0.userId() );
        }
        if ( arg0.expiryDate() != null ) {
            arg1.setExpiryDate( arg0.expiryDate() );
        }
    }
}
