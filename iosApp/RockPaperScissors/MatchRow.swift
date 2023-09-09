import RPSKit
import SwiftUI

struct MatchRow: View {

	let model: MatchRowComponentModel

	var body: some View {
		VStack(spacing: 10) {
			Text(model.rounds)
				.foregroundColor(model.isLive ? .red : .gray)
				.font(.caption)
				.fontWeight(.bold)
				.fontDesign(.monospaced)

			HStack {
				Group {
					Text(model.participantA.flag)
					Text(model.participantA.name)
						.frame(maxWidth: .infinity, alignment: .leading)
					Spacer()
					Text(String(model.participantA.score))
						.fontDesign(.monospaced)
						.foregroundColor(model.isLive ? .red : nil)
				}
				.font(.system(.body, weight: model.participantA.isWinner ? .heavy : .regular))

				Text("-")
					.font(.system(.body, design: .monospaced))
					.foregroundColor(model.isLive ? .red : nil)

				Group {
					Text(String(model.participantB.score))
						.fontDesign(.monospaced)
						.foregroundColor(model.isLive ? .red : nil)
					Spacer()
					Text(model.participantB.name)
						.frame(maxWidth: .infinity, alignment: .trailing)
					Text(model.participantB.flag)
				}
				.font(.system(.body, weight: model.participantB.isWinner ? .heavy : .regular))
			}
		}
		.padding(.vertical, 8)
		.padding(.horizontal, 16)
	}
}

extension MatchRowComponentModel: Identifiable {}

struct MatchRow_Previews: PreviewProvider {

	static var previews: some View {
		VStack {
			MatchRow(
				model: .init(
					participantA: .init(
						name: "Jana",
						score: "3",
						flag: "🇸🇰",
						isWinner: true
					),
					participantB: .init(
						name: "Petr",
						score: "2",
						flag: "🇨🇿",
						isWinner: false
					),
					rounds: "ROUND 42",
					isLive: false,
					configuration: nil
				)
			)

			MatchRow(
				model: .init(
					participantA: .init(
						name: "Alex",
						score: "0",
						flag: "🇸🇨",
						isWinner: false
					),
					participantB: .init(
						name: "Steve",
						score: "0",
						flag: "🇱🇰",
						isWinner: false
					),
					rounds: "ROUND 999",
					isLive: true,
					configuration: nil
				)
			)
		}
	}
}
