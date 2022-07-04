import lombok.SneakyThrows;
import service.DocHelperService;


public class DocHelperApp {
    @SneakyThrows
    public static void main(String[] args) {
        DocHelperService docHelperService = new DocHelperService();
    }
}
