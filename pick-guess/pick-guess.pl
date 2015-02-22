#!/usr/bin/perl
use Switch;
use IO::Handle;


my @possible_feedback = ('+', '-');
my @range = (1, 100);
my $guess = 50;
my $times_guess = 0;

sub print_value {
	my $value = shift(@_);
	STDOUT->printflush($value, "\n");
}


sub binary_guess {
	my $feedback = shift(@_);
	my ($low, $high) = @range;
	switch ($feedback) {
		case "+" { $low = $guess < $range[1] ? $guess+1 : $guess }
		case "-" { $high = $guess > $range[0] ? $guess-1 : $guess; }
		else     { }
	}
	{
		use integer;
		$guess = ($low + $high) / 2;
	}
	@range = ($low, $high);
	return $guess;
}

sub bin_qua_guess {
	my $feedback = shift(@_);

	switch($times_guess) {
		case 1 {
			binary_guess($feedback);
			my $assumed_feeback = int(rand(0+@possible_feedback));
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
			@range = calculate_solvable_range(4);
			binary_guess();
		}
		else  { 
			binary_guess($feedback);
		}
	}
	return $guess;
}

sub calculate_solvable_range {
	my $max_attempts = shift(@_);	
	my $size = 2**$max_attempts - 1;
	my ($low, $high) = @range;
	my $sections = int(($high - $low) / $size);
	my $selected = int(rand($sections));

	$low = $low + $selected*$size;
	$high = $low + $size;
	if ($high > $range[1]) {
		$high = $range[1];
	}
	return ($low, $high);
}

sub almost_binary_guess {
	my $feedback = shift(@_);
	my $max_deviation = 4;
	my $deviation = -$max_deviation + int(rand($max_deviation*2));
	my $bin_guess = binary_guess($feedback);
	my ($low, $high) = @range;
	$guess = $bin_guess + $deviation;
	if ($guess > $high) {
		$guess = $high;
	}
	if ($guess < $low) {
		$guess = $low;
	}
	return $guess
}


my %guessers = (
	binary => \&binary_guess,
	bin_qua => \&bin_qua_guess,
	solvable => \&solvable_guess,
	almost_bin => \&almost_binary_guess
);

sub guess {
	#XXX: why?
	$guesser = (keys %guessers)[rand keys %guessers];
	$guesser = almost_bin;

	# Give the initial guess
	$guessers{$guesser}->($feedback);
	print "The range is @range \n";
	$times_guess = 1;
	print_value($guess);
	
	# Update the guess
	while (chomp(my $feedback = <STDIN>)) {
		if ($feedback ne "=") {
			my $guess = $guessers{$guesser}->($feedback);
			$times_guess = $times_guess + 1;
			print "The range is @range \n";
			print_value($guess);
		} else {
			last;
		}
	}
}

sub random_pick {
	my ($low, $high) = @range;
	my $pick = int(rand($high)) + $low;
	return $pick;
}

sub binary_expand {
	my @pivots = @_;
	my @expanded = ();
	my $len = $#pivots;
	for my $i (0 .. $len) {
		push @expanded, $pivots[$i];
		if ($i != $len) {
			push @expanded, int(($pivots[$i] + $pivots[$i+1])/2);
		}
	}
	return @expanded;
}

sub nobinary_pick {
	my @expanded = @range;
	for my $i (0..5) {
		@expanded = binary_expand(@expanded);		
	}
	my @all = (1..100);

	my %count = ();
	my @diff = ();
	foreach $elem (@expanded, @all) { $count{$elem}++ }
	foreach $elem (keys %count) {
		if ($count{$elem} == 1) {
			push @diff, $elem;
		}
	}

	my $selected = int(rand($#diff));
	return $diff[$selected];
}


my %pickers = (
	random => \&random_pick,
	nobinary => \&nobinary_pick
);


sub pick {
	#XXX: why?
	$picker = (keys %pickers)[rand keys %pickers];

	my $pick = $pickers{$picker}->();
	print_value($pick);
}

# Pick or guess?
my ($mode) = @ARGV;

switch($mode) {
	case "pick" 	{ pick() }
	case "guess"	{ guess() } 
}

