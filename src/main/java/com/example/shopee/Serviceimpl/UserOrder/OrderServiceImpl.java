package com.example.shopee.Serviceimpl.UserOrder;

import com.example.shopee.DTO.Address.AddressResponseDTO;
import com.example.shopee.DTO.UserOrder.OrderItemDTO;
import com.example.shopee.DTO.UserOrder.OrderRequestDTO;
import com.example.shopee.DTO.UserOrder.OrderResponseDTO;
import com.example.shopee.Exception.ResourceNotFoundException;
import com.example.shopee.Mapper.OrderMapper;
import com.example.shopee.Model.Address;
import com.example.shopee.Model.Product;
import com.example.shopee.Model.User;
import com.example.shopee.Model.UserOrder.Order;
import com.example.shopee.Model.UserOrder.OrderItem;
import com.example.shopee.Repo.AddressRepo;
import com.example.shopee.Repo.ProductRepository;
import com.example.shopee.Repo.UserOrder.OrderRepo;
import com.example.shopee.Repo.UserRepo;
import com.example.shopee.Service.UserOrder.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.shopee.Model.UserOrder.OrderStatus.PLACED;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepo orderRepo;
    private final ProductRepository productRepo;
    private final AddressRepo addressRepo;
    private final UserRepo userRepo;

    @Override
    public ResponseEntity<OrderResponseDTO> placeOrder(OrderRequestDTO orderRequestDTO, String username) {

        User user = userRepo.findByUsername(username).orElseThrow(()-> new UsernameNotFoundException("User not found"));

        Address address = addressRepo.findById(orderRequestDTO.getAddressId()).orElseThrow(()-> new ResourceNotFoundException("Address not found"));

        Order order = new Order();
        order.setUser(user);
        order.setAddress(address);
        order.setTotalAmount(orderRequestDTO.getTotalAmount());
        order.setStatus(PLACED);
        order.setOrderDate(LocalDateTime.now());

        List<OrderItem> orderItems = orderRequestDTO.getItems().stream().map(
                itemDto-> {
                    Product product = productRepo.findById(itemDto.getProductId()).orElseThrow(()-> new ResourceNotFoundException("Product not found"));

                    OrderItem orderItem = new OrderItem();
                    orderItem.setQuantity(itemDto.getQuantity());
                    orderItem.setOrder(order);
                    orderItem.setProduct(product);
                    return orderItem;
                }
        ).toList();
        order.setItems(orderItems);
        Order tempOrder = orderRepo.save(order);

        return ResponseEntity.ok(OrderMapper.convertToDto(tempOrder));
    }

    public ResponseEntity<List<OrderResponseDTO>> getOrdersByUsername(String username){
        User user = userRepo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        List<Order> orders = orderRepo.findByUser(user);

        return ResponseEntity.ok(orders.stream().map(order -> {
            OrderResponseDTO dto = new OrderResponseDTO();
            dto.setOrderId(order.getId());
            dto.setOrderDate(order.getOrderDate());
            dto.setTotalAmount(order.getTotalAmount());
            dto.setStatus(order.getStatus().name());

            AddressResponseDTO addressResponseDTO = new AddressResponseDTO(order.getAddress());
            dto.setAddress(addressResponseDTO);

            List<OrderItemDTO> items = order.getItems().stream().map(item->{
                OrderItemDTO orderItemDTO = new OrderItemDTO();
                orderItemDTO.setProductId(item.getProduct().getId());
                orderItemDTO.setProductName(item.getProduct().getName());
                orderItemDTO.setQuantity(item.getQuantity());
                return orderItemDTO;
            }).toList();
            dto.setItems(items);

            return dto;
        }).toList());

    }

}
