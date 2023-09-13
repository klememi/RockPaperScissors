import RPSKit

extension RPSViewState {

	static let placeholder: RPSViewState = .init(
		title: "Loading...",
		rows: [],
		language: AppConfiguration.companion.default.languages[0],
		project: AppConfiguration.companion.default.projects[0]
	)
}
