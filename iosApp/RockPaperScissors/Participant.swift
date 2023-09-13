import RPSKit
import SwiftUI

struct Participant: View {

	let model: MatchRowComponentModel.Participant
	let isLive: Bool

	var body: some View {
		HStack(spacing: 0) {
			Text(model.flag)
				.padding(.trailing, 8)
			Text(model.name)
				.frame(maxWidth: .infinity, alignment: .leading)
			Spacer()
			Text(String(model.score))
				.fontDesign(.monospaced)
				.foregroundColor(isLive ? .red : nil)
		}
		.font(.system(.body, weight: model.isWinner ? .heavy : .regular))
	}
}
