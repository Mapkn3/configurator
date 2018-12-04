package my.mapkn3.configurator.view;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.web.servlet.view.AbstractView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.util.Map;

public abstract class AbstractDocxView extends AbstractView {
    public AbstractDocxView() {
        initDocx();
    }

    private void initDocx() {
        setContentType("application/msword"); //setContentType("application/vnd.openxmlformats-officedocument.wordprocessingml.document");
    }

    @Override
    protected boolean generatesDownloadContent() {
        return true;
    }

    @Override
    protected final void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        ByteArrayOutputStream baos = createTemporaryOutputStream();

        XWPFDocument docxDocument = buildDocxDocument(model, request, response);
        docxDocument.write(baos);

        response.setHeader("Content-Disposition", "attachment");
        writeToResponse(response, baos);
    }

    protected abstract XWPFDocument buildDocxDocument(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception;
}
