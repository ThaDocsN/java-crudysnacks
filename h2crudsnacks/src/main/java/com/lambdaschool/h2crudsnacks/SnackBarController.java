package com.lambdaschool.h2crudsnacks;

import com.lambdaschool.h2crudsnacks.models.Customer;
import com.lambdaschool.h2crudsnacks.models.Snack;
import com.lambdaschool.h2crudsnacks.models.VendingMachine;
import com.lambdaschool.h2crudsnacks.repository.CustomerRepository;
import com.lambdaschool.h2crudsnacks.repository.SnackRepository;
import com.lambdaschool.h2crudsnacks.repository.VendingMachineRepository;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Api(value = "Crudy SnackBar Application", description = "The classic Snack Bar Application in CRUD")
@RestController
@RequestMapping(path = {}, produces = MediaType.APPLICATION_JSON_VALUE)
public class SnackBarController
{
    @Autowired
    CustomerRepository custrepos;

    @Autowired
    SnackRepository snackrepos;

    @Autowired
    VendingMachineRepository vendingrepos;

    // GET    /customer - returns all customers *
    // GET    /customer/id/{id} - returns customer based on id *
    // GET    /customer/name/{name} - returns customer based on name *
    // POST   /customer - adds a customer *
    // PUT    /customer/id/{id} - updates customer based on id *
    // DELETE /customer/{id} - delete customer based on id


    @ApiOperation(value = "list All customer", response = List.class)
    @ApiResponses(value =
            {
                    @ApiResponse(code = 200, message = "Successfully recetrieve list"),
                    @ApiResponse(code = 401, message = "You are not authorized to the view the resource"),
                    @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
                    @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
            })

    @GetMapping("/customer")
    public List<Customer> allcust()
    {
        return custrepos.findAll();
    }



    @ApiOperation(value = "Customer based off of customer id", response = Customer.class)
    @GetMapping("/customer/id/{id}")
    public Customer findCustId(
            @ApiParam(value = "This is the customer you seek", required = true) @PathVariable long id)
    {
        var foundCust = custrepos.findById(id);
        if (foundCust.isPresent())
        {
            return foundCust.get();
        }
        else
        {
            return null;
        }
    }

    @GetMapping("/customer/name/{name}")
    public Customer findCustomer(@PathVariable String name)
    {
        var foundCust = custrepos.findByName(name);
        if (foundCust != null)
        {
            return foundCust;
        }
        else
        {
            return null;
        }
    }

    @PostMapping("/customer")
    public Customer newCustomer(@RequestBody Customer customer) throws URISyntaxException
    {
        return custrepos.save(customer);
    }

    @PutMapping("/customer/id/{id}")
    public Customer changeCust(@RequestBody Customer newcust, @PathVariable long id) throws URISyntaxException
    {
        Optional<Customer> updateCust = custrepos.findById(id);
        if (updateCust.isPresent())
        {
            newcust.setId(id);
            custrepos.save(newcust);

            return newcust;
        }
        else
        {
            return null;
        }
    }

    @DeleteMapping("/customer/id/{id}")
    public Customer deleteCustomer(@PathVariable long id)
    {
        var foundCust = custrepos.findById(id);
        if (foundCust.isPresent())
        {
            custrepos.deleteById(id);
            return foundCust.get();
        }
        else
        {
            return null;
        }
    }

    @GetMapping("/snack")
    public List<Snack> allsnacks()
    {
        return snackrepos.findAll();
    }

    @GetMapping("/snack/vending")
    public List<Object[]> vendingSnacks()
    {
        return snackrepos.vendingSnacks();
    }

    @DeleteMapping("/snack/{id}")
    public void deleteSnack(@PathVariable Long id)
    {
        snackrepos.deleteById(id);
    }

    // GET    /vending - returns all vending machines *
    // GET    /vending/id/{id} - returns vending machine based on id *
    // GET    /vending/name/{name} - returns vending machine based on name *
    // POST   /vending - adds a vending machine *
    // PUT    /vending/id/{} - updates vending machine based on id *
    // DELETE /vending/{id} - delete vending machine based on id
    // since vending machine is returning a list of snacks, return value must be list

    @GetMapping("/vending")
    public List<VendingMachine> allvending()
    {
        return vendingrepos.findAll();
    }

    @GetMapping("/vending/id/{id}")
    public List<VendingMachine> getVendId(@PathVariable long id)
    {
        return vendingrepos.findById(id).stream().collect(Collectors.toList());
    }

    @GetMapping("/vending/{name}")
    public List<VendingMachine> namedvending(@PathVariable String name)
    {
        return vendingrepos.findByName(name);
    }

    @PostMapping("/vending")
    public VendingMachine newVending(@RequestBody VendingMachine vendingMachine) throws URISyntaxException
    {
        return vendingrepos.save(vendingMachine);
    }

    @PutMapping("/vending/id/{id}")
    public List<VendingMachine> changeVending(@RequestBody VendingMachine newVending, @PathVariable long id) throws URISyntaxException
    {
        Optional<VendingMachine> updatedVending = vendingrepos.findById(id);
        if (updatedVending.isPresent())
        {
            newVending.setId(id);
            vendingrepos.save(newVending);

            return java.util.Arrays.asList(newVending);
        }
        else
        {
            return updatedVending.stream().collect(Collectors.toList());
        }
    }

    @DeleteMapping("/vending/{id}")
    public List<VendingMachine> deleteVendingMachine(@PathVariable long id)
    {
        List<VendingMachine> rmvending = vendingrepos.findById(id).stream().collect(Collectors.toList());
        vendingrepos.deleteById(id);
        return rmvending;
    }
}
