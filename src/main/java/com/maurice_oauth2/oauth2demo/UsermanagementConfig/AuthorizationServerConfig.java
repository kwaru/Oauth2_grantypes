package com.maurice_oauth2.oauth2demo.UsermanagementConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig  extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private AuthenticationManager authenticationManager;


    /**
     * Grant type = > How the client gets the Token.
     * There are several ways how a client get toke.(Grant types).
     * 1.authorization code( improved password)
     * 2. Client credentials.
     * 3. Refresh Token.
     * 4. Implicit (Deprecated)
     * 5. Password (Deprecated)
     * @param clients
     * @throws Exception
     */

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory().
                //password grant_type Oauth2 client
                withClient("clientId").secret("secret1").scopes("read")
                .authorizedGrantTypes("password")


                .and()

                //authorization_code grant_type Oauth2
                .withClient("client2")
                .secret("secret2")
                .scopes("read")
                .authorizedGrantTypes("authorization_code")
                .redirectUris("http:localhost:8087/auth_token/acesscode")
        .and()
                // refresh token grant_type Oauth2
                .withClient("client3").secret("client3")
        .scopes("read").authorizedGrantTypes("client_credentials")
        .and().withClient("client4").secret("client4").scopes("read")
                .authorizedGrantTypes("password","refresh_token");

        /**
         * Used to configure clients that will be allowed to access Tokens from Authorization server.
         */
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints){
        endpoints.authenticationManager(authenticationManager);
    }
}
