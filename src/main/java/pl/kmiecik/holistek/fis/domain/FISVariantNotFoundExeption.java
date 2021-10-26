package pl.kmiecik.holistek.fis.domain;

public class FISVariantNotFoundExeption extends RuntimeException {

    public FISVariantNotFoundExeption(String message) {
        super("FIS server  response : "+ message);
    }


}
