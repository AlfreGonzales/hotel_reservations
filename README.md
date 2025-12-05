## Hotel Reservation API
Proyecto de reserva de hoteles.


### Generación de Token para pruebas

Para la generación del token usamos [jwt.io](https://www.jwt.io/).

Para ello necesitamos cumplir las siguientes configuraciones.

#### Header

```
{
"alg": "HS256",
"typ": "JWT"
}
```
#### Payload

En este punto tenemos que asegurarnos mandar una propiedad role, que contiene el rol dentro de la aplicacion:
```aiignore
{
  "sub": "1234567890",
  "iat": 1516239022,
  "name": "Pedro Perez",
  "role": "ADMIN" | "HOTEL_ADMIN" | "GUEST"
}
```
### Secret

En esto punto se tiene que mandar la clave secreta de 256 bits para generar el token.