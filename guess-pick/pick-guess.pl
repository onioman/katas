#!/usr/bin/perl
use Switch;

my @range = (1, 100);
my $guess = 50;

sub binary_guess {
	my $feedback = shift(@_);
	my $low = $range[0];
	my $high = $range[1];
	switch ($feedback) {
		case "+" { @range[0] = $guess+1 }
		case "-" { @range[1] = $guess-1 }
	}
	$low = $range[0];
	$high = $range[1];
	{
		use integer;
		$guess = ($low + $high) / 2;
	}
	print $guess, "\n";
}

my %guessers = (
	binary => \&binary_guess
);

sub guess {
	# Give the initial guess
	print $guess, "\n";
	
	# Update the guess
	while (chomp(my $feedback = <STDIN>)) {
		if ($feedback ne "=") {
			$guessers{binary}->($feedback);
		} else {
			last;
		}
	}
}

# Pick or guess?
my ($mode) = @ARGV;

switch($mode) {
	case "pick" 	{ print 34, "\n"; exit 0 }
	case "guess"	{ guess() } 
}

