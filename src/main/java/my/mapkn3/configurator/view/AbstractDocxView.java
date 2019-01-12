package my.mapkn3.configurator.view;

import org.springframework.web.servlet.view.AbstractView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public abstract class AbstractDocxView extends AbstractView {
    public AbstractDocxView() {
        initDocx();
    }

    private void initDocx() {
        setContentType("application/msword");
    }

    @Override
    protected boolean generatesDownloadContent() {
        return true;
    }

    @Override
    protected final void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        ByteArrayOutputStream baos = createTemporaryOutputStream();

        String document = buildDocxDocument(model, request, response);
        baos.write(document.getBytes());


        response.setHeader("Content-Disposition", "attachment; filename=" + new SimpleDateFormat("ddMMyy_-_HHmmss").format(new Date()) + ".doc");
        writeToResponse(response, baos);
    }

    protected abstract String buildDocxDocument(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception;
}
