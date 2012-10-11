//
//  ViewController.m
//  DataViewer
//
//  Created by testuser on 10/6/12.
//  Copyright (c) 2012 z2d2sky. All rights reserved.
//

#import "ViewController.h"
#import "DataManager.h"
#import "DataModel.h"
#import "Car.h"

@interface ViewController ()

@end


@implementation ViewController

static NSMutableArray* data = nil;
static DataManager* dm = nil;
static int currentIndex = -1;
static int lastIndex = 0;
static bool statScreen = NO;
//static NSEnumerator* forwardIter = nil;
//static NSEnumerator* reverseIter = nil;

// For the main screen
@synthesize make, model, year, hybrid;

// For the Stats screen
@synthesize total_hybrid_cars, total_cars, average_years;

- (void)viewDidLoad
{
    [super viewDidLoad];
    //Init the data manager
    if(!statScreen)
        [self initDataManager];
    else
        [self generateStats];
    statScreen = YES;
}

/* Initializes the Data Manager, obtains a pointer to the Data
 * Initializes the screen GUI with the first Car object in the array  */
- (void) initDataManager
{
    dm = [[DataManager alloc] init];
    [dm populateDataModel];
    data = [[dm dataModel] data];
    lastIndex = data.count;
    //forwardIter = [data objectEnumerator];
    //reverseIter = [data reverseObjectEnumerator];

    [self updateUIWithThisCar: [self nextCar]];
}

/* Obtains a pointer to the Next Car object from the Array
 * If the array reaches the last Element, then it alerts and DOES NOT give the next object
 */
- (Car*) nextCar
{
    if(currentIndex == data.count-1)
    {
        UIAlertView *alert = [[UIAlertView alloc] initWithTitle: @"Invalid Selection" message:@"Reached last Car" delegate:nil cancelButtonTitle:@"Ok" otherButtonTitles:nil, nil];
        [alert show];
    } else {
        currentIndex++;
    }
    return (Car*)[data objectAtIndex:currentIndex];
}

/* Obtains a pointer to the Previous Car object in the array
 * If the array reaches the first Element, then it alerts and DOES NOT give the previous object
 */
- (Car*) prevCar
{
    if(currentIndex == 0)
    {
        UIAlertView *alert = [[UIAlertView alloc] initWithTitle: @"Invalid Selection" message:@"Reached First Car" delegate:nil cancelButtonTitle:@"Ok" otherButtonTitles:nil, nil];
        [alert show];
    } else {
        currentIndex--;
    }
    return (Car*)[data objectAtIndex:currentIndex];
}

/* Hybrid On/Off switch state changed Action */
- (IBAction) switchStateChanged:(id)sender
{
    Car *car = (Car*)[data objectAtIndex:currentIndex];
    [car setHybrid:hybrid.on];
    //NSLog(@"Hybrid state changed: %d",car.hybrid);
}

/* Next Button Action */
- (IBAction) nextButton:(id)sender {
    Car* car = [self nextCar];
    [self updateUIWithThisCar:car];
}

/* Previous Button Action */
- (IBAction) prevButton:(id)sender
{
    Car* car = [self prevCar];
    [self updateUIWithThisCar:car];
}

/* Gets you to the Stats screen */
- (IBAction) showStatsButton :(id)sender
{
    statScreen = YES;
    [self generateStats];
}

/* Gets you back from the Stats view of the car to the main screen */
- (IBAction) back :(id)sender
{
    [self.presentingViewController dismissViewControllerAnimated:YES completion:nil];
}

/* Updates the GUI with the Car object passed as argument */
- (void) updateUIWithThisCar: (Car*) car
{
    make.text = car.make;
    model.text = car.model;
    year.text = [[NSString alloc] initWithFormat:@"%d",car.year];
    [hybrid setOn: car.hybrid];
}

/* Generates the stats for the cars for the stats screen */
- (void) generateStats {
    Car *car;
    int tot_cars, tot_hybrid_cars, average_year;
    tot_hybrid_cars = 0; average_year = 0;
    tot_cars = data.count;
    for (int i=0; i<tot_cars; i++) {
        car = (Car*) [data objectAtIndex:i];
        average_year += car.year;
        if(car.hybrid) {
            tot_hybrid_cars++;
        }
    }
    average_year /= tot_cars;

    total_cars.text = [[NSString alloc] initWithFormat:@"%d",tot_cars];
    total_hybrid_cars.text = [[NSString alloc] initWithFormat:@"%d",tot_hybrid_cars];
    average_years.text = [[NSString alloc] initWithFormat:@"%d",average_year];
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

@end
