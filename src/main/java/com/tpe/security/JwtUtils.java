package com.tpe.security;

import com.tpe.security.service.UserDetailsImpl;
import io.jsonwebtoken.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtils {

    private String jwtSecret ="sboot";
    private long jwtExpirationMs = 86400000;// !!! 24 * 60 * 60 * 1000 ( 1 gun )

    // Not:  ****************** GENERATE TOKEN ***************
    public String generateToken(Authentication authentication){
        //!!! Authenticate edilen kullanici Authenticate nesnesi olarak SecurityContext e atiliyor,
            // bu yuzden bu methoda parametre olarak ekleniyor.
        //!!! anlık olarak login olarak kullanıcının bilgisini alıyorum,
            // sebebi : Token üretirken username bilgisini kullanacağım
         UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

         return Jwts.builder().
                 setSubject(userDetails.getUsername()). //token username ile olusturuluyor
                 setIssuedAt(new Date()).
                 setExpiration(new Date(new Date().getTime() + jwtExpirationMs)).
                 signWith(SignatureAlgorithm.HS512, jwtSecret).
                 compact();
    }

    // Not: ******************* VALIDATE TOKEN ***************
    /*
    Valide edilirken yapilan adimlar :

        1)  Tokenin Yapısının Kontrolü: İlk adımda, JWT'nin doğru bir şekilde üç bölümden (header, payload, signature)
        oluşup oluşmadığı kontrol edilir. Bu bölümler nokta işareti (.) ile ayrılır.

        2)  İmzanın Doğrulanması: JWT, oluşturulurken belirlenen bir algoritma ve secret key (gizli anahtar)
        kullanılarak imzalanır. Sunucu, kendisine iletilen tokenin imzasını, aynı algoritma ve secret key kullanarak
        doğrular. Eğer imza geçerliyse, tokenin içeriği güvenilir kabul edilir. Bu adım, tokenin bütünlüğünün
        korunduğunu ve değiştirilmediğini garanti eder.

        3)  Son Kullanma Tarihinin Kontrolü (Expiration Time - exp): JWT'nin payload kısmında, tokenin ne zaman sona
        ereceğini belirten bir son kullanma tarihi (exp) bulunabilir. Tokenin geçerlilik süresi kontrol edilir ve
        sürenin dolmuş olması durumunda token reddedilir.
 */ // Validate Adimlari aciklamasi
    public boolean validateToken(String token){

        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
        /*
                * Jwts.parser() --> JWT'ları ayrıştırmak için kullanılan bir parser (ayrıştırıcı)
                    nesnesi oluşturur. JWT, genellikle üç bölümden oluşur: Başlık (Header),
                    Yük (Payload), ve İmza (Signature). parser metodu, bu üç bölümü ayrıştırarak
                    tokenin yapısını analiz eder.

                 * setSigningKey(jwtSecret) --> , JWT'nin doğrulanması sırasında kullanılacak
                     olan imza anahtarını (signing key) ayarlar.

                 * parseClaimsJws(token) -->, verilen token değerini ayrıştırır ve doğrular.
                    Bu süreçte, öncelikle token'ın imzası, ayarlanan jwtSecret anahtarı
                    kullanılarak kontrol edilir. Eğer imza geçerliyse, token'ın içeriği
                    ayrıştırılır.

                 * Token başarıyla doğrulandıktan sonra, içerisinde bulunan "claims"
                    erişilebilir hale gelir. Claims, token içinde saklanan ve kullanıcının
                    kimliği, yetkileri veya diğer önemli bilgileri içeren veri parçalarıdır.

             */ // kodda kullanilan methodlarin aciklamasi
            return true;
        } catch (ExpiredJwtException e) {
            throw new RuntimeException(e);
        } catch (UnsupportedJwtException e) {
            throw new RuntimeException(e);
        } catch (MalformedJwtException e) {
            throw new RuntimeException(e);
        } catch (SignatureException e) {
            throw new RuntimeException(e);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(e);
        }

    }

    // Not: ****************** GET USERNAME FROM TOKEN *******
    public String getUserNameFromJwtToken(String token){
        return Jwts.parser().
                setSigningKey(jwtSecret).
                parseClaimsJws(token).
                getBody().
                getSubject();
    }
}
