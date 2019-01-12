package my.mapkn3.configurator.view;

import org.springframework.web.servlet.view.AbstractView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public abstract class AbstractDocView extends AbstractView {
    public AbstractDocView() {
        initDoc();
    }

    private void initDoc() {
        setContentType("application/msword");
    }

    @Override
    protected boolean generatesDownloadContent() {
        return true;
    }

    @Override
    protected final void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        ByteArrayOutputStream baos = createTemporaryOutputStream();

        Date now = new Date(new Date().getTime() + new Date(TimeUnit.HOURS.toMillis(4)).getTime());
        String document = buildDocDocument(model, request, response, now);
        baos.write(document.getBytes());

        response.setHeader("Content-Disposition", "attachment; filename=" + new SimpleDateFormat("ddMMyy_-_HHmmss").format(now) + ".doc");
        writeToResponse(response, baos);
    }

    protected abstract String buildDocDocument(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response, Date time) throws Exception;
}
