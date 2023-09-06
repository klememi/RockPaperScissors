import RPSKit

struct Project: Hashable {
	let code: String
	let name: String
}

struct Lang: Hashable {
	let code: String
	let name: String
}

extension [MatchRowComponentModel] {

	static let placeholder: Self = [
		.init(
			participantA: .init(
				name: "participantA",
				score: "0",
				flag: "🇺🇦",
				isWinner: false
			),
			participantB: .init(
				name: "participantB",
				score: "0",
				flag: "🇺🇦",
				isWinner: false
			),
			rounds: "round 0",
			isLive: false,
			configuration: nil
		)
	]
}
