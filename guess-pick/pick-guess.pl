#!/usr/bin/perl
use Switch;

my @possible_feedback = ('+', '-');
my @range = (1, 100);
my $guess = 50;

sub binary_guess {
	my $feedback = shift(@_);
	my ($low, $high) = @range;
	switch ($feedback) {
		case "+" { $low = $guess+1 }
		case "-" { $high = $guess-1 }
		else     { }
	}
	{
		use integer;
		$guess = ($low + $high) / 2;
	}
	@range = ($low, $high);
	return $guess;
}


my $times_guess = 0;
my @real_range = (1, 100);

sub bin_qua_guess {
	my $feedback = shift(@_);

	switch($times_guess) {
		case 1 {
			binary_guess($feedback);
			@real_range = @range;
			my $assumed_feeback = int(rand(2));
			binary_guess($possible_feedback[$assumed_feeback]);
		}
		else  { 
			binary_guess($feedback);
		}
	}

	return $guess;
}

sub solvable_guess {
	my $feedback = shift(@_);
	switch($times_guess) {
		case 1 {
			binary_guess($feedback);
			@real_range = @range;
			@range = calculate_solvable_range();
			binary_guess();
		}
		else  { 
			binary_guess($feedback);
		}
	}
	return $guess;
}

sub calculate_solvable_range {
	my $size = 15;
	my ($low, $high) = @range;
	my $sections = int(($high - $low) / $size);
	my $selected = int(rand($sections));

	$low = $low + $selected*$size;
	$high = $low + $size;
	if ($high > $real_range[1]) {
		$high = $real_range[1];
	}
	return ($low, $high);
}


my %guessers = (
	binary => \&binary_guess,
	bin_qua => \&bin_qua_guess,
	solvable => \&solvable_guess
);

sub guess {
	#XXX: why?
	$guesser = (keys %guessers)[rand keys %guessers];

	# Give the initial guess
	$guessers{$guesser}->($feedback);
	$times_guess = 1;
	print $guess,"\n";
	
	# Update the guess
	while (chomp(my $feedback = <STDIN>)) {
		if ($feedback ne "=") {
			my $guess = $guessers{$guesser}->($feedback);
			$times_guess = $times_guess + 1;
			print $guess, "\n";
		} else {
			last;
		}
	}
}

sub pick {
	my ($low, $high) = @range;
	my $pick = int(rand($high)) + $low;
	print $pick,"\n"	
}

# Pick or guess?
my ($mode) = @ARGV;

switch($mode) {
	case "pick" 	{ pick() }
	case "guess"	{ guess() } 
}

