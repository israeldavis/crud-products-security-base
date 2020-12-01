package com.crud.product.controllers;

import com.crud.product.entities.Product;
import com.crud.product.services.ProductsPDFExporter;
import com.crud.product.services.ProductsService;
import com.lowagie.text.DocumentException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class ProductsController {

    private final ProductsService productsService;

    private boolean mostrarImagen = false;

    public ProductsController(ProductsService productsService) {
        this.productsService = productsService;
    }


    @GetMapping("/")
    public String index(Model model) {
        List<Product> productos = productsService.getProducts();
        model.addAttribute("mostrarImagen", mostrarImagen);
        model.addAttribute("productos", productos);
        return "index";
    }

    @GetMapping("/productos/{id}")
    public String getProduct(@PathVariable Long id, Model model) {
        Product product = productsService.getProduct(id);
        model.addAttribute("producto", product);
        return "detalles";
    }

    @GetMapping("/productos/editar/{id}")
    public String getEditProduct(@PathVariable Long id, Model model ) {
        Product product = productsService.getProduct(id);
        model.addAttribute("product", product);
        return "editar";
    }

    @PostMapping("/productos/guardar")
    public String guardar(@Valid Product product, Errors errores) {

        if(errores.hasErrors()) {
            if(product.getId() == null || product.getId().equals("") ) {
                //model.addAttribute("producto", product);
                return "newproduct";
            }
            else {
                //model.addAttribute("producto", product);
                return "editar";
            }
        }

        Product productoGuardado = productsService.guardar(product);

        System.out.println("El producto: " + productoGuardado );

        return "redirect:/";
    }

    @GetMapping("/productos/create")
    public String nuevoProducto(Model model) {
        model.addAttribute("product", new Product());

        return "newproduct";
    }

    @GetMapping("/productos/borrar/{id}")
    public String borrarProducto(@PathVariable Long id) {
        productsService.delete(id);
        return "redirect:/";
    }

    @PostMapping("/toggleImage")
    public String toggleImage() {
        this.mostrarImagen = !this.mostrarImagen;
        return "redirect:/";
    }

    @GetMapping("/403")
    public String erorr403(){
        return "/errores/403";
    }

    @GetMapping("/products/export/pdf")
    public void exportToPdf(HttpServletResponse response) throws DocumentException, IOException {
        response.setContentType("application/pdf");
        DateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=products_" + currentDateTime + ".pdf";
        response.setHeader(headerKey, headerValue);

        List<Product> listProducts = productsService.getProducts();

        ProductsPDFExporter exporter = new ProductsPDFExporter(listProducts);
        exporter.export(response);
    }
}
