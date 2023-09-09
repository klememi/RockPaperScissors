import RPSKit
import SwiftUI

struct ContentView: View {

	@StateObject
	var viewModel: ViewModel

    var body: some View {
		List(viewModel.viewState.rows.reversed()) { match in
			MatchRow(model: match)
		}
		.modified(by: viewModel)
		.task { await viewModel.run() }
    }
}

private extension View {

	func modified(by viewModel: ViewModel) -> some View {
		NavigationView {
			self
				.toolbar {
					ToolbarItem(placement: .navigationBarLeading) {
						Picker(
							"",
							selection: .init(
								get: { viewModel.viewState.project },
								set: { viewModel.send(RPSStateManagerEventSetProject(project: $0)) }
							)
						) {
							ForEach(viewModel.projects, id: \.name) {
								Text($0.name)
									.tag($0)
							}
						}
					}
				}
				.toolbar {
					ToolbarItem(placement: .navigationBarTrailing) {
						Picker(
							"",
							selection: .init(
								get: { viewModel.viewState.language },
								set: { viewModel.send(RPSStateManagerEventSetLanguage(language: $0)) }
							)
						) {
							ForEach(viewModel.languages, id: \.name) {
								Text($0.name)
									.tag($0)
							}
						}
					}
				}
				.navigationTitle(viewModel.viewState.title)
		}
	}
}
