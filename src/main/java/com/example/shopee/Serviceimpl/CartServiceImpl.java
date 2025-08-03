package com.example.shopee.Serviceimpl;

import com.example.shopee.DTO.Cart.CartDto;
import com.example.shopee.Exception.ResourceNotFoundException;
import com.example.shopee.Mapper.CartMapper;
import com.example.shopee.Model.Cart;
import com.example.shopee.Model.CartItem;
import com.example.shopee.Model.Product;
import com.example.shopee.Model.User;
import com.example.shopee.Repo.CartRepo;
import com.example.shopee.Repo.ProductRepository;
import com.example.shopee.Repo.UserRepo;
import com.example.shopee.Service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final UserRepo userRepo;
    private final CartRepo cartRepo;
    private final ProductRepository productRepo;


    // Method to add product to user using username and productId
    @Override
    public CartDto addToCart(String username, Long productId, int quantity) {

        User user = userRepo.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));

        Cart cart = cartRepo.findByUser(user).orElseGet(() -> {
            Cart tempCart = new Cart();
            tempCart.setUser(user);
            return cartRepo.save(tempCart);
        });

        Product product = productRepo.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        Optional<CartItem> existingItem = cart.getCartItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst();

        if (existingItem.isPresent()) {
            existingItem.get().setQuantity(existingItem.get().getQuantity() + 1);
        } else {
            CartItem item = new CartItem();
            item.setProduct(product);
            item.setQuantity(1);
            item.setCart(cart);
            cart.getCartItems().add(item);
        }

        return CartMapper.convertToDto(cartRepo.save(cart));
    }

    @Override
    public CartDto getCartByUsername(String username) {
        User user = userRepo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        Cart cart = cartRepo.findByUser(user)
                .orElseGet(() -> {
                    Cart tempCart = new Cart();
                    tempCart.setUser(user);
                    return cartRepo.save(tempCart);
                });
        return CartMapper.convertToDto(cart);


    }

    @Override
    public CartDto changeQuantity(String username, Long productId, int value) {
        User user = userRepo.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));

        Cart cart = cartRepo.findByUser(user).orElseThrow(() -> new ResourceNotFoundException("Cart not found"));

        Optional<CartItem> tempItem = cart.getCartItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst();

        if (tempItem.isEmpty()) {
            throw new ResourceNotFoundException("Product not found in cart");
        }

        CartItem item = tempItem.get();
        int updatedQuantity = item.getQuantity() + value;

        if (updatedQuantity <= 0) {
            cart.getCartItems().remove(item);
        } else {
            item.setQuantity(updatedQuantity);
        }

        Cart updatedCart = cartRepo.save(cart);

        return CartMapper.convertToDto(updatedCart);
    }

    @Override
    public CartDto removeItem(String username, Long productId) {
        User user = userRepo.findByUsername(username).orElseThrow(()->new UsernameNotFoundException("User not found"));

        Cart cart = cartRepo.findByUser(user).orElseThrow(()-> new ResourceNotFoundException("Cart not found"));

        Optional<CartItem> tempItem = cart.getCartItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst();

        if (tempItem.isEmpty()) {
            throw new ResourceNotFoundException("Product not found in cart");
        }

        CartItem item = tempItem.get();
        cart.getCartItems().remove(item);
        Cart updatedCart = cartRepo.save(cart);
        return CartMapper.convertToDto(updatedCart);

    }


}
