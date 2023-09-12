import RPSKit

extension RPSViewState {

	static let placeholder: RPSViewState = .init(
		title: "",
		rows: [
			.init(
				participantA: .init(
					name: "Mišo",
					score: "0",
					flag: "🇸🇨",
					isWinner: false
				),
				participantB: .init(
					name: "Martin",
					score: "1",
					flag: "🇸🇧",
					isWinner: false
				),
				rounds: "KOLO 927 ",
				isLive: true,
				configuration: nil
			)
		],
		language: AppConfiguration.companion.default.languages[0],
		project: AppConfiguration.companion.default.projects[0]
	)
}
