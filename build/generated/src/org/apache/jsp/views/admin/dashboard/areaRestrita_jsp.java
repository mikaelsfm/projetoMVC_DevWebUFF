package org.apache.jsp.views.admin.dashboard;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import entidade.Administrador;

public final class areaRestrita_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List<String> _jspx_dependants;

  private org.glassfish.jsp.api.ResourceInjector _jspx_resourceInjector;

  public java.util.List<String> getDependants() {
    return _jspx_dependants;
  }

  public void _jspService(HttpServletRequest request, HttpServletResponse response)
        throws java.io.IOException, ServletException {

    PageContext pageContext = null;
    HttpSession session = null;
    ServletContext application = null;
    ServletConfig config = null;
    JspWriter out = null;
    Object page = this;
    JspWriter _jspx_out = null;
    PageContext _jspx_page_context = null;

    try {
      response.setContentType("text/html;charset=UTF-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;
      _jspx_resourceInjector = (org.glassfish.jsp.api.ResourceInjector) application.getAttribute("com.sun.appserv.jsp.resource.injector");

      out.write("\n");
      out.write("\n");
      out.write("<!DOCTYPE html>\n");
      out.write("<html lang=\"pt-br\">\n");
      out.write("\n");
      out.write("<head>\n");
      out.write("    <meta charset=\"UTF-8\">\n");
      out.write("    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n");
      out.write("    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n");
      out.write("    <link rel=\"shortcut icon\" href=\"#\">\n");
      out.write("    <title>Área Restrita</title>\n");
      out.write("    <link href=\"http://localhost:8080/aplicacaoMVC/views/bootstrap/bootstrap.min.css\" rel=\"stylesheet\">\n");
      out.write("</head>\n");
      out.write("\n");
      out.write("<body>\n");
      out.write("    <div class=\"container mt-5\">\n");
      out.write("        ");
      org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "../../comum/menu.jsp", out, false);
      out.write("\n");
      out.write("        <div class=\"mt-4\">\n");
      out.write("            <h1 class=\"text-primary\">Área Restrita</h1>\n");
      out.write("            ");

                Administrador administradorLogado = (Administrador) session.getAttribute("administrador");
                out.println("<h3 class='text-success'>Usuário logado com sucesso</h3>");
                out.println("<h2>Nome: " + administradorLogado.getNome() + "</h2>");
            
      out.write("\n");
      out.write("            \n");
      out.write("            <!-- Formulário para adicionar aluno -->\n");
      out.write("            <div class=\"card mt-4 shadow-sm\">\n");
      out.write("                <div class=\"card-header bg-primary text-white\">\n");
      out.write("                    <h4>Adicionar Aluno</h4>\n");
      out.write("                </div>\n");
      out.write("                <div class=\"card-body\">\n");
      out.write("                    <form action=\"adicionarAluno.jsp\" method=\"post\">\n");
      out.write("                        <div class=\"mb-3\">\n");
      out.write("                            <label for=\"id\" class=\"form-label\">ID:</label>\n");
      out.write("                            <input type=\"number\" id=\"id\" name=\"id\" class=\"form-control\" required8>\n");
      out.write("                        </div>\n");
      out.write("                        <div class=\"mb-3\">\n");
      out.write("                            <label for=\"nome\" class=\"form-label\">Nome:</label>\n");
      out.write("                            <input type=\"text\" id=\"nome\" name=\"nome\" class=\"form-control\" maxlength=\"50\" required>\n");
      out.write("                        </div>\n");
      out.write("                        <div class=\"mb-3\">\n");
      out.write("                            <label for=\"email\" class=\"form-label\">E-mail:</label>\n");
      out.write("                            <input type=\"email\" id=\"email\" name=\"email\" class=\"form-control\" maxlength=\"50\" required>\n");
      out.write("                        </div>\n");
      out.write("                        <div class=\"mb-3\">\n");
      out.write("                            <label for=\"celular\" class=\"form-label\">Celular:</label>\n");
      out.write("                            <input type=\"text\" id=\"celular\" name=\"celular\" class=\"form-control\" maxlength=\"14\" placeholder=\"(99) 99999-9999\" required>\n");
      out.write("                        </div>\n");
      out.write("                        <div class=\"mb-3\">\n");
      out.write("                            <label for=\"cpf\" class=\"form-label\">CPF:</label>\n");
      out.write("                            <input type=\"text\" id=\"cpf\" name=\"cpf\" class=\"form-control\" maxlength=\"14\" placeholder=\"999.999.999-99\" required>\n");
      out.write("                        </div>\n");
      out.write("                        <div class=\"mb-3\">\n");
      out.write("                            <label for=\"senha\" class=\"form-label\">Senha:</label>\n");
      out.write("                            <input type=\"password\" id=\"senha\" name=\"senha\" class=\"form-control\" maxlength=\"255\" required>\n");
      out.write("                        </div>\n");
      out.write("                        <div class=\"mb-3\">\n");
      out.write("                            <label for=\"endereco\" class=\"form-label\">Endereço:</label>\n");
      out.write("                            <input type=\"text\" id=\"endereco\" name=\"endereco\" class=\"form-control\" maxlength=\"50\">\n");
      out.write("                        </div>\n");
      out.write("                        <div class=\"mb-3\">\n");
      out.write("                            <label for=\"cidade\" class=\"form-label\">Cidade:</label>\n");
      out.write("                            <input type=\"text\" id=\"cidade\" name=\"cidade\" class=\"form-control\" maxlength=\"30\">\n");
      out.write("                        </div>\n");
      out.write("                        <div class=\"mb-3\">\n");
      out.write("                            <label for=\"bairro\" class=\"form-label\">Bairro:</label>\n");
      out.write("                            <input type=\"text\" id=\"bairro\" name=\"bairro\" class=\"form-control\" maxlength=\"30\">\n");
      out.write("                        </div>\n");
      out.write("                        <div class=\"mb-3\">\n");
      out.write("                            <label for=\"cep\" class=\"form-label\">CEP:</label>\n");
      out.write("                            <input type=\"text\" id=\"cep\" name=\"cep\" class=\"form-control\" maxlength=\"9\" placeholder=\"99999-999\">\n");
      out.write("                        </div>\n");
      out.write("                        <div class=\"d-grid\">\n");
      out.write("                            <button type=\"submit\" class=\"btn btn-primary\">Salvar</button>\n");
      out.write("                        </div>\n");
      out.write("                    </form>\n");
      out.write("                </div>\n");
      out.write("            </div>\n");
      out.write("        </div>\n");
      out.write("    </div>\n");
      out.write("    <script src=\"http://localhost:8080/aplicacaoMVC/views/bootstrap/bootstrap.bundle.min.js\"></script>\n");
      out.write("</body>\n");
      out.write("\n");
      out.write("</html>\n");
    } catch (Throwable t) {
      if (!(t instanceof SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          out.clearBuffer();
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
