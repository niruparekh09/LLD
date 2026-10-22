package service;

import domain.PricingRule;
import domain.Ticket;
import domain.Vehicle;
import repository.PricingRuleRepository;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;

public class PricingService {
    private PricingRuleRepository pricingRuleRepository;

    public PricingService(PricingRuleRepository pricingRuleRepository) {
        this.pricingRuleRepository = pricingRuleRepository;
    }

    public double calculateFee(Ticket ticket) {
        System.out.println("{SERVICE} Calculating fee for ticket: " + ticket);

        // Ideally we will get a persisted vehicle via this.
        // Vehicle vehicle = vehicalRepository.getVehcial(ticket.getVehicleId());
        // Vehicle.VehicleType type = vehicle.getVehicleType();

        // For demo purposes, we'll use a dummy vehicle type
        // In a real system, you'd get the vehicle from the ticket
        Vehicle.VehicleType vehicleType = Vehicle.VehicleType.CAR; // Default

        Optional<PricingRule> rule = pricingRuleRepository.findByVehicleType(vehicleType);

        PricingRule pricingRule = rule.orElseThrow(() -> new IllegalStateException("No pricing rule present for vehicle type: " + vehicleType));

        // Calc both flat and hourly rate
        double flatFee = pricingRule.getFlatRate();
        double hourlyFee = calculateHourlyFee(ticket, pricingRule.getRatePerHour());

        double finalFee = Math.min(flatFee, hourlyFee);

        System.out.println("[SERVICE] Flat fee: " + flatFee + ", Hourly fee: " + hourlyFee + ", Final fee: " + finalFee + " for vehicle type: " + vehicleType);

        return finalFee;
    }

    private double calculateHourlyFee(Ticket ticket, double ratePerHour) {
        Duration duration = Duration.between(ticket.getEntryTime(), LocalDateTime.now()); // Calculating total time
        long hours = duration.toHours();

        // Min 1 hour charge
        if (hours < 1) hours = 1;

        return hours * ratePerHour;
    }
}
