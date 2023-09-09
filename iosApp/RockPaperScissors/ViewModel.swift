import RPSKit
import SwiftUI

final class ViewModel: ObservableObject {

	@Published
	private(set) var viewState: RPSViewState = .placeholder

	private let viewStateProvider: RPSViewStateProvider = .init()

	func send(_ event: RPSStateManagerEvent) {
		viewStateProvider.changeState(event: event)
	}

	@MainActor
	func run() async {
		for await response in viewStateProvider.getViewState() {
			if let viewState = response.dataOrNull() {
				self.viewState = viewState
			}
		}
	}
}

extension ViewModel {

	var languages: [Language] { AppConfiguration.companion.default.languages }
	var projects: [Project] { AppConfiguration.companion.default.projects }
}
