import SwiftUI

struct ContentView: View {

	@StateObject
	var viewModel: ViewModel

    var body: some View {
		NavigationView {
			List(viewModel.matches.reversed()) { match in
				MatchRow(model: match)
			}
			.redacted(reason: viewModel.isLoading ? .placeholder : [])
			.toolbar {
				ToolbarItem(placement: .navigationBarTrailing) {
					Picker(
						"",
						selection: .init(
							get: { viewModel.selectedLang },
							set: { viewModel.send(.setLang($0)) }
						)
					) {
						ForEach(viewModel.languages, id: \.name) {
							Text($0.name)
								.tag($0)
						}
					}
				}
			}
			.toolbar {
				ToolbarItem(placement: .navigationBarLeading) {
					Picker(
						"",
						selection: .init(
							get: { viewModel.selectedProject },
							set: { viewModel.send(.setProject($0)) }
						)
					) {
						ForEach(viewModel.projects, id: \.name) {
							Text($0.name)
								.tag($0)
						}
					}
				}
			}
			.navigationTitle(viewModel.title)
		}
		.task {
			await viewModel.run()
		}
    }

}
