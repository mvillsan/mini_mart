package com.example.sandoval;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.sandoval.data.DatabaseHandler;
import com.example.sandoval.model.Product;

import java.util.List;

public class AddProducts extends AppCompatActivity {

    ImageButton homeBtn;
    Button cmdAddProduct;
    EditText prodName, prodPrice, prodQuantity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_products);

        refs();

        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                home();
            }
        });

        cmdAddProduct.setOnClickListener(addNewProduct);
    }

    public void refs(){
        homeBtn = findViewById(R.id.homeBtn3);
        cmdAddProduct = findViewById(R.id.addProdBtn);
        prodName = findViewById(R.id.prodNameTxt);
        prodPrice = findViewById(R.id.priceTxt);
        prodQuantity = findViewById(R.id.quantityTxt);
    }
    public void home(){
        Intent intent = new Intent(getApplicationContext(), HomeProducts.class);
        startActivity(intent);
    }

    View.OnClickListener addNewProduct = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String productName = prodName.getText().toString();

            if(productName.isEmpty()) {
                Toast.makeText(getApplicationContext(), "PLEASE INPUT A PRODUCT NAME!", Toast.LENGTH_SHORT).show();
            }
            else if(!productName.matches("[A-Za-z]+")){
                Toast.makeText(getApplicationContext(), "INVALID PRODUCT NAME!", Toast.LENGTH_SHORT).show();
            }
            else{
                Product product = new Product();
                product.setName(prodName.getText().toString());
                product.setPrice(Double.parseDouble(prodPrice.getText().toString()));
                product.setQuantity(Integer.parseInt(prodQuantity.getText().toString()));

                DatabaseHandler db = new DatabaseHandler(AddProducts.this);
                if(db.addProduct(product)) {
                    Toast.makeText(getApplicationContext(), "Successfully Added", Toast.LENGTH_SHORT).show();

                    List<Product> productList =  db.getAllProducts();

                    for(Product prod: productList) {
                        Log.d("ProductActivity", "On Create: " + prod.getName());
                    }
                }
                prodName.setText("");
                prodPrice.setText("");
                prodQuantity.setText("");
            }
        }
    };
}