import KMPNativeCoroutinesAsync
import RPSKit

final class ViewModel: ObservableObject {

	enum Action {
		case setLang(Lang)
		case setProject(Project)
	}

	@Published
	private(set) var matches: [MatchRowComponentModel] = .placeholder

	@Published
	private(set) var title: String = ""

	@Published
	private(set) var isLoading = true

	private(set) var selectedLang: Lang
	private(set) var selectedProject: Project

	let languages: [Lang] = [
		.init(code: "EN", name: "🇬🇧"),
		.init(code: "CZ", name: "🇨🇿")
	]

	let projects: [Project] = [
		.init(code: "GL", name: "Global"),
		.init(code: "CZ", name: "Czech"),
		.init(code: "BR", name: "Brazil"),
	]

	private let viewStateProvider: RPSViewStateProvider

	init() {
		let lang = languages[0]
		let project = projects[0]
		selectedLang = lang
		selectedProject = project
		viewStateProvider = RPSViewStateProvider(lang: lang.code, project: project.code)
	}

	func send(_ action: Action) {
		switch action {
		case .setLang(let lang):
			selectedLang = lang
			viewStateProvider.changeState(event: RPSStateManagerEventSetLang(lang: lang.code))

		case .setProject(let project):
			selectedProject = project
			viewStateProvider.changeState(event: RPSStateManagerEventSetProject(project: project.code))
		}
	}

	@MainActor
	func run() async {
		do {
			for try await response in asyncSequence(for: viewStateProvider.getViewState()) {
				if let viewState = response.dataOrNull() {
					matches = viewState.rows
					title = viewState.title
					isLoading = false
				}
			}
		} catch {}
	}
}
